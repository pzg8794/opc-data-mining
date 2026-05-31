from django.conf.urls import patterns, url
from searchPlace import views

urlpatterns = patterns('',
url(r'^$', views.index, name='index'),
#url(r'^index$', views.index, name='index'),
#url(r'^after_login/$', views.after_login,name='after_login'),

                       
) 