from django import forms
from models import place, searchplace
from django.contrib.admin import widgets
class SearchPlaceForm(forms.ModelForm):
    
    class Meta:
        model = searchplace        
        fields = ('city', 'fromDate', 'toDate')        
    def __init__(self, *args, **kwargs):
        
        #self.initial = kwargs.pop('initial', None)
        #age = self.initial['age']
        #gender = self.initial['gender']
        
        super(SearchPlaceForm, self).__init__(*args, **kwargs)
        self.fields['fromDate'].widget = widgets.AdminDateWidget()
        self.fields['fromDate'].widget.attrs['class']= "box"
        
        self.fields['city'].widget.attrs['class']= "city_select"
        
        
        self.fields['toDate'].widget = widgets.AdminDateWidget()
        self.fields['toDate'].widget.attrs['class']= "box"
                

class PlaceForm(forms.ModelForm):
    class Meta:
        model = place
        fields = ('city', 'slug', 'longitude', 'latitude','image') 