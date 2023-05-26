FROM python:3.10

WORKDIR /app
RUN mkdir /app/data
COPY requirements.txt .
COPY *.py .

RUN pip install -r requirements.txt
CMD ["python3", "main.py"]