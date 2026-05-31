from django.conf.urls import patterns, include, url
from city import views

urlpatterns = patterns('',
url(r'^$', views.index, name='index'),
url(r'^index$', views.index, name='index'),
url(r'^searchPlace$', views.searchPlace,name='searchPlace'),
url(r'^placeListing', views.placeListing,name='placeListing'),
url(r'^createPlace', views.createPlace,name='createPlace'),
url(r'^saveDataUser', views.saveDataUser,name='saveDataUser'),


) 