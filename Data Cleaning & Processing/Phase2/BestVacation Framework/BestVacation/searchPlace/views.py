# Create your views here.
from django.http import HttpResponse
from django.template import RequestContext, loader
from django.shortcuts import render_to_response, get_object_or_404
from django.contrib.auth.decorators import login_required
from searchPlace.models import UserProfile
def index(request):
    user=''
    if request.user.is_authenticated():
        user = "Wander"
    latest_FacebookUser_list = UserProfile.objects.order_by('name')[:5]
    template = loader.get_template('searchPlace/index.html')
    context = RequestContext(request, {
                                       'latest_FacebookUser_list':latest_FacebookUser_list,
                                       })
    return HttpResponse(template.render(context))
#@login_required(login_url='/searchPlace/index/')
#def after_login(request):
#    return HttpResponse("Hello world")
#    user = get_object_or_404(FacebookUser, pk=1) 
#    return render_to_response('searchPlace/after_login.html', {'FacebookUser': user})