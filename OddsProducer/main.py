#! /usr/bin/env python3
import json
import time

import requests

from football import Football


def send_web_backend(json_data, consumer_server):

    print("===========================================")
    print("=== Odds collected, send to consumer ======")
    print("===========================================")

    r = requests.post(consumer_server, json=json_data)


def main(server_address, consumer_server):

    driver = setup_webdriver(server_address)

    try:
        f = Football(driver)
        links = f.get_match_hyperlink()
        full_books = f.get_books(links)

        json_data = ([obj.__dict__ for obj in full_books])

        print(json_data)

        send_web_backend(json_data, consumer_server)

    finally:
        driver.quit()

    # must ensure the driver quit, cuz it is a session


def setup_webdriver(server_address):
    from selenium import webdriver
    from selenium.webdriver.firefox.options import Options as FirefoxOptions
    options = FirefoxOptions()
    # options.add_argument("--headless")
    driver = webdriver.Remote(command_executor=server_address, options=options)
    return driver


if __name__ == '__main__':
    import os

    print("===========================================")
    print("========= Program initializing ===========")
    print("===========================================")

    webdriver_server = os.getenv("driver_server")
    consumer_server = os.getenv("consumer_server")

    while True:
        main(webdriver_server, consumer_server)
        time.sleep(30)
