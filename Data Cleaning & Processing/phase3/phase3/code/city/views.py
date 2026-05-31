# Create your views here.
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.template import RequestContext, loader
from django.shortcuts import render_to_response, get_object_or_404
from django.contrib.auth.decorators import login_required
from city.models import userprofile, place, data
from forms import SearchPlaceForm, PlaceForm
from django.core.context_processors import csrf
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger
from django.views.decorators.csrf import csrf_exempt
from django.db.models import Avg
from django_facebook import exceptions as facebook_exceptions, \
    settings as facebook_settings
from django_facebook.connect import CONNECT_ACTIONS, connect_user
from django_facebook.decorators import facebook_required_lazy
from django_facebook.utils import next_redirect, get_registration_backend, \
    to_bool, error_next_redirect, get_instance_for
    
from django_facebook.models import FacebookModel
from matplotlib import pylab
from pylab import *
import numpy
import PIL
import PIL.Image
import StringIO


import logging
logger = logging.getLogger(__name__)

@csrf_exempt
@facebook_required_lazy
def connect(request, graph):
    '''
    Exception and validation functionality around the _connect view
    Separated this out from _connect to preserve readability
    Don't bother reading this code, skip to _connect for the bit you're interested in :)
    '''
    backend = get_registration_backend()
    context = RequestContext(request)

    # validation to ensure the context processor is enabled
    if not context.get('FACEBOOK_APP_ID'):
        message = 'Please specify a Facebook app id and ensure the context processor is enabled'
        raise ValueError(message)

    try:
        response = _connect(request, graph)
    except open_facebook_exceptions.FacebookUnreachable, e:
        # often triggered when Facebook is slow
        warning_format = u'%s, often caused by Facebook slowdown, error %s'
        warn_message = warning_format % (type(e), e.message)
        send_warning(warn_message, e=e)
        additional_params = dict(fb_error_or_cancel=1)
        response = backend.post_error(request, additional_params)

    return response


def _connect(request, graph):
    '''
    Handles the view logic around connect user
    - (if authenticated) connect the user
    - login
    - register

    We are already covered by the facebook_required_lazy decorator
    So we know we either have a graph and permissions, or the user denied
    the oAuth dialog
    '''
    backend = get_registration_backend()
    context = RequestContext(request)
    connect_facebook = to_bool(request.REQUEST.get('connect_facebook'))

    logger.info('trying to connect using Facebook')
    if graph:
        logger.info('found a graph object')
        converter = get_instance_for('user_conversion', graph)
        authenticated = converter.is_authenticated()
        # Defensive programming :)
        if not authenticated:
            raise ValueError('didnt expect this flow')

        logger.info('Facebook is authenticated')
        facebook_data = converter.facebook_profile_data()
        # either, login register or connect the user
        try:
            action, user = connect_user(
                request, connect_facebook=connect_facebook)
            logger.info('Django facebook performed action: %s', action)
        except facebook_exceptions.IncompleteProfileError, e:
            # show them a registration form to add additional data
            warning_format = u'Incomplete profile data encountered with error %s'
            warn_message = warning_format % unicode(e)
            send_warning(warn_message, e=e,
                         facebook_data=facebook_data)

            context['facebook_mode'] = True
            context['form'] = e.form
            return render_to_response(
                backend.get_registration_template(),
                context_instance=context,
            )
        except facebook_exceptions.AlreadyConnectedError, e:
            user_ids = [u.get_user_id() for u in e.users]
            ids_string = ','.join(map(str, user_ids))
            additional_params = dict(already_connected=ids_string)
            return backend.post_error(request, additional_params)

        response = backend.post_connect(request, user, action)

        if action is CONNECT_ACTIONS.LOGIN:
            pass
        elif action is CONNECT_ACTIONS.CONNECT:
            # connect means an existing account was attached to facebook
            messages.info(request, _("You have connected your account "
                                     "to %s's facebook profile") % facebook_data['name'])
        elif action is CONNECT_ACTIONS.REGISTER:
            # hook for tying in specific post registration functionality
            response.set_cookie('fresh_registration', user.id)
    else:
        # the user denied the request
        additional_params = dict(fb_error_or_cancel='1')
        response = backend.post_error(request, additional_params)

    return response
def index(request):    
    template = loader.get_template('city/index.html')
    context = RequestContext(request)
    #print user.first_name
    #request.session["facebookid"] = user.profile_or_self.facebook_id
    return HttpResponse(template.render(context))

def saveDataUser(request):    
    request.session['User-gender'] = request.POST.get("gender", "")
    request.session['User-dob'] = request.POST.get("dob", "").split(',')[-1]
    print request.session['User-gender'],request.session['User-dob'] 
    return HttpResponseRedirect("/city/searchPlace")
        
@login_required(login_url='/city/index')
def searchPlace(request):   
    if request.POST:
        form = SearchPlaceForm(request.POST, initial={'age':request.session['User-dob'],'gender':request.session['User-gender']})
        if form.is_valid():      
            data_ = form.cleaned_data
            city = data_['city']
            fromDate = data_['fromDate']
            toDate = data_['toDate']           
            
            day2 = toDate.day
            month2 = toDate.month
            
            day1 = fromDate.day
            month1 = fromDate.month
            
            
                       
            #object_list = data.objects.filter(city_id = city, day__range= [day1, day2], month__range =  [ month1, month2])
            list_elements = []
            prec_elements = []
            wind_elements = []
            x_axis = []            
            
            for x in range(day1,day2+1):            
                t1 = data.objects.filter(city_id = city, day = x , month =  month1).aggregate(Avg('tmax'))
                t2 = data.objects.filter(city_id = city, day = x , month =  month1).aggregate(Avg('prec'))
                t3 = data.objects.filter(city_id = city, day = x , month =  month1).aggregate(Avg('wind'))
                
                list_elements.append(t1['tmax__avg'])
                prec_elements.append(t2['prec__avg'])
                wind_elements.append(t3['wind__avg'])
                x_axis.append(x)                    
            
            #post_values = request.POST.copy()                        
            #print request.session['User-gender'],request.session['User-dob']
            
            #post_values['gender'] = request.session['User-gender']
            #post_values['age'] = request.session['User-dob']
            #post_values['avg_tp'] = numpy.mean(list_elements)
            
            #form2 = SearchPlaceForm(post_values)
            
            #form2.save()
            form.save()
            
            plot(x_axis,list_elements,linewidth=2)
            xlabel('days')
            ylabel('Temperature')
            title('Temperature estimated')
            grid(True)
                       
            pylab.savefig('city/static/city/images/plots/plot1.png')
            pylab.close()
            
            plot(x_axis,prec_elements,linewidth=2)
            xlabel('days')
            ylabel('Precipitation')
            title('Precipitation estimated')
            grid(True)
                       
            pylab.savefig('city/static/city/images/plots/plot2.png')
            pylab.close()
            
            plot(x_axis,wind_elements,linewidth=2)
            xlabel('days')
            ylabel('Wind')
            title('Wind estimated')
            grid(True)
                       
            pylab.savefig('city/static/city/images/plots/plot3.png')
            pylab.close()
            
            return render_to_response('city/dataListing.html', {"list": list_elements, 'x_axis': x_axis}) 
                        
    else:
        form = SearchPlaceForm(initial={'age':request.session['User-dob'],'gender':request.session['User-gender']})
    args = {}
    args.update(csrf(request))
    
    args['form'] = form
    return render_to_response('city/searchPlace.html', args)

def createPlace(request):
    if request.POST:
        form = PlaceForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/searchPlace/placeListing')      
    else:
        form = PlaceForm()
    args = {}
    args.update(csrf(request))
    
    args['form'] = form
    return render_to_response('city/createPlace.html', args)
 
def placeListing(request):
    object_list = place.objects.all()
    paginator = Paginator(object_list, 5) # Show 25 contacts per page

    page = request.GET.get('page')
    try:
        places = paginator.page(page)
    except PageNotAnInteger:
        # If page is not an integer, deliver first page.
        places = paginator.page(1)
    except EmptyPage:
        # If page is out of range (e.g. 9999), deliver last page of results.
        places = paginator.page(paginator.num_pages)

    return render_to_response('city/placeListing.html', {"places": places})
"""
def graph(request):
    x = [1,2, 3, 4, 5, 6]
    y = [5, 2, 6, 8, 2, 7]
    plot(x,y,linewidth=2)
    xlabel('x axis')
    ylabel('y axis')
    title('sample graph')
    grid(True)
    #pylab.show()
    buffer = StringIO.StringIO()
    canvas = pylab.get_current_fig_manager().canvas
    canvas.draw()
    graphIMG = PIL.Image.fromstring("RGB", canvas.get_width_height(), canvas.tostring_rgb())
    graphIMG.save(buffer, "PNG")
    pylab.savefig('city/static/city/images/plots/plot1.png')
    pylab.close()
    return HttpResponse(buffer.getvalue(), mimetype="image/png")
"""    