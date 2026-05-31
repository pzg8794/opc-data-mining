from django.contrib import admin
from city.models import place

class PlaceAdmin(admin.ModelAdmin):
    list_display = ('city', 'thumbnail','latitude', 'longitude')
    list_filter = ('city',)
    search_fields = ['city']
    prepopulated_fields = {'slug':('city',)}    
#    fields = ('city','latitude','longitude','slug',)

admin.site.register(place, PlaceAdmin) 