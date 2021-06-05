from django.db import models

# Create your models here.
class UserCheckUp(models.Model):
    username=models.CharField(max_length=200,default='vvv@gmail.com', unique=True)
    check=models.CharField(max_length=2000,default='Nothing')
