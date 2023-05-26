#! /usr/bin/env python3
import json
import time
import datetime
import requests
import os

from football import Football


def save_the_data(json_data):
    print("===========================================")
    print("=== Odds collected, save data        ======")
    print("===========================================")

    # r = requests.post(json=json_data)
    os.chdir("/data")

    now = datetime.datetime.now()

    with open(f"{now.year}-{now.month}-{now.day}-{now.hour}-{now.minute}", "w") as file_out:
        file_out.write(json_data)


def main(server_address):
    driver = setup_webdriver(server_address)

    try:
        f = Football(driver)
        links = f.get_match_hyperlink()
        full_books = f.get_books(links)

        json_data = ([obj.__dict__ for obj in full_books])

        print(json_data)

        save_the_data(json_data)

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

    while True:
        main(webdriver_server)
        time.sleep(30)
