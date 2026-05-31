from django.conf import settings
from django.db import models
from django.db.models.signals import post_save
from django.dispatch import receiver
from django_facebook.models import FacebookModel, get_user_model
from django_facebook.utils import get_profile_model
from django.utils.safestring import mark_safe
import Image
import logging
logger = logging.getLogger(__name__)

try:
    from django.contrib.auth.models import AbstractUser, UserManager
    class customfacebookuser(AbstractUser, FacebookModel):
        objects = UserManager()
        # add any customizations you like
        state = models.CharField(max_length=255, blank=True, null=True)
except ImportError, e:
    logger.info('Couldnt setup FacebookUser, got error %s', e)
    pass


# Create your models here.
class userprofile(FacebookModel):
    '''
Inherit the properties from django facebook
'''
    user = models.OneToOneField(settings.AUTH_USER_MODEL)
@receiver(post_save)
def create_profile(sender, instance, created, **kwargs):
    """Create a matching profile whenever a user object is created."""
    if sender == get_user_model():
        user = instance
        profile_model = get_profile_model()
        if profile_model == userprofile and created:
            profile, new = userprofile.objects.get_or_create(user=instance)
            
class place(models.Model):
    
    def url(self, filename):
        route = 'city/static/city/images/%s'%(str(filename))
        return route
    def thumbnail(self):
        return mark_safe('<a href="%s"><img src="%s" width=50px height=50px /></a>')%(self.image,self.image)
    
    thumbnail.allow_tags= True
    city = models.CharField(max_length=50)
    slug = models.SlugField(unique=True)
    longitude = models.DecimalField(max_digits=5, decimal_places=2)
    latitude = models.DecimalField(max_digits=5, decimal_places=2)
    image = models.ImageField(upload_to = url, null = True, blank = True)
    def __unicode__(self):
        return self.city
    
class searchplace(models.Model):    
    city = models.ForeignKey(place)
    fromDate = models.DateField()
    toDate = models.DateField()
    age = models.IntegerField(blank=True)
    gender = models.CharField(max_length=1, blank=True)
    avg_tp = models.IntegerField(blank=True)
    created_on = models.DateTimeField(auto_now_add=True)
    
    #def __unicode__(self):
    #    return '%s %s %s %s %s' % (self.city, self.age, self.gender, self.avg_tp, self.created_on)   
class data(models.Model):
    city = models.ForeignKey(place)
    year = models.IntegerField()
    month = models.IntegerField()
    day = models.IntegerField()
    prec = models.IntegerField()
    tmax = models.IntegerField()
    tmin = models.IntegerField()
    wind = models.IntegerField()
    def __unicode__(self):
        return '%s %s %s' % (self.month, self.day, self.year)    