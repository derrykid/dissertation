FROM python:3.8

WORKDIR /app
RUN mkdir /app/data
COPY requirements.txt .
COPY *.py .

RUN pip3 install -r requirements.txt
CMD ["python3", "main.py"]