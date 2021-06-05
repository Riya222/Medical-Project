from django.shortcuts import render
from django.http import JsonResponse,HttpResponse
from .models import *
from django.views.decorators.csrf import csrf_exempt
from django.core import serializers
from PIL import Image
from pytesseract import image_to_string

# Create your views here.
bb = 0
@csrf_exempt
def getimage(request):
    w = ""
    print("inside checkup")
    a=request.POST.get("uploaddf")
    u=request.POST.get("user")
    print(u)
##    d=request.POST.get('date')
    import base64
    IMAGE_DIMS = (96, 96, 3)
    imgdata = base64.b64decode(a)
    
    filename = 'res.jpg'  # I assume you have a way of picking unique filenames
    with open(filename, 'wb') as f:
        f.write(imgdata)
    s = image_to_string(Image.open(filename), lang='eng')
    if s.strip() == "":
      s = "Nothing."
    if s!=w:
        w = s
        d = {}
        try:
            
            ob = UserCheckUp(username = u, check = s).save()
            l=[]
            l.append({"check": s, "prev": "No previous record", "user":u})
            d["data"]=l
        except Exception as ex:
            print("Error",ex)
            ob=UserCheckUp.objects.get(username = u)
            l=[]
            b = ob.check
            print("Values: ",ob.check)
            l.append({"check": s, "prev":b, "user":u})
            d["data"]=l
            ob.check = s
            ob.save()
            
        print("Out:",d)
        #print(ob.check)
        return JsonResponse(d,safe=False)

