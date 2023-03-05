import json
import boto3
import csv
import io
import datetime
import botocore

s3Client = boto3.client('s3')

def lambda_handler(event, context):
    print(event)
    print(context)
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = event['Records'][0]['s3']['object']['key'].rsplit('.', 1)[0]
    print(bucket)
    print(key)

    key_status = key + '.status'
    try:
        response = s3Client.get_object(Bucket=bucket, Key=key_status)
    except botocore.exceptions.ClientError as e:
        time = datetime.datetime.now().isoformat()
        payload = 'File ' + key + ' created on ' + time
        response = s3Client.put_object(Body=payload, Bucket=bucket, Key=key_status)
        return

    time = datetime.datetime.now().isoformat()
    payload = 'File ' + key + ' updated on ' + time
    response = s3Client.put_object(Body=payload, Bucket=bucket, Key=key_status)