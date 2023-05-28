#! /usr/bin/env python3
import json
import time
import datetime
import requests
import os

from football import Football


def save_the_data(json_string):
    print("===========================================")
    print("=== Odds collected, save data        ======")
    print("===========================================")

    # r = requests.post(json=json_data)
    os.chdir("/app/data")

    now = datetime.datetime.now()
    file_name = f"{now.year}-{now.month}-{now.day}-{now.hour}-{now.minute}"

    with open(file_name, "w") as file_out:
        file_out.write(json_string)

    print("Save successfully")

def main(server_address):
    driver = setup_webdriver(server_address)

    try:
        f = Football(driver)
        links = f.get_match_hyperlink()
        full_books = f.get_books(links)

        json_string = json.dumps([obj.__dict__ for obj in full_books])

        # print(json_data)

        save_the_data(json_string)

    finally:
        driver.quit()

    # must ensure the driver quit, cuz it is a session


def setup_webdriver(server_address):
    from selenium import webdriver
    from selenium.webdriver.firefox.options import Options as FirefoxOptions
    from fake_useragent import UserAgent

    options = FirefoxOptions()
    options.add_argument("--headless")
    options.add_argument('--disable-blink-features=AutomationControlled')

    ua = UserAgent()
    userAgent = ua.random
    print(userAgent)
    options.add_argument(f'user-agent={userAgent}')

    driver = webdriver.Remote(command_executor=server_address, options=options)
    time.sleep(3)
    return driver


if __name__ == '__main__':
    import os

    print("===========================================")
    print("========= Program initializing ===========")
    print("===========================================")

    # webdriver_server = "http://localhost:4444"
    webdriver_server = os.getenv("driver_server")

    while True:
        main(webdriver_server)
        time.sleep(30)
