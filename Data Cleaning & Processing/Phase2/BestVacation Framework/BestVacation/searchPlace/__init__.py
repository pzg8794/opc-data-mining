"""
from django.contrib import admin
from searchPlace.models import Person

class PersonAdmin(admin.ModelAdmin):
    fieldsets = [
              (None,                {'fields' : ['user']}),
              ('User Information',  {'fields' : ['name'], 'classes' : ['collapse']}),
              ]
admin.site.register(Person,PersonAdmin)
"""

"""
App ID:     215476235293925
App Secret:     6390f11a38809235d30749f9e07d1128 
"""