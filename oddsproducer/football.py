import time

from selenium.webdriver.common.by import By

from bookmaker import Bookmaker


def get_teams(title):
    parts = title.split("Betting Odds, Football - Premier League")
    print("-------")
    print(title)
    print(parts)

    parts = [e.strip() for e in parts]
    teams = parts[0].split("-")

    parts = [e.strip() for e in teams]
    home = parts[0]
    away = parts[1]
    return home, away


class Football:
    base_address = "https://www.oddsportal.com/football/england/premier-league/"

    def __init__(self, webdriver):
        self.__webdriver__ = webdriver

    def get_match_hyperlink(self) -> set:
        time.sleep(1)
        self.__webdriver__.get(self.base_address)
        time.sleep(1)
        match_links = set()

        i = 0
        while i < 5:
            self.__webdriver__.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(0.5)
            self.__webdriver__.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            i = i + 1

        self.__webdriver__.execute_script("window.scrollTo(0, -document.body.scrollHeight);")
        time.sleep(0.3)

        # TODO, change back to 100
        for i in range(1, 5):
            # There are 2 forms, the xpath might be like 'target1' or 'target2'
            xpath1 = '/html/body/div[1]/div/div[1]/div/main/div[2]/div[5]/div[1]/div[{}]/div/div/div[1]/div[2]/div/div/a[1]'.format(i)
            xpath2 = '/html/body/div[1]/div/div[1]/div/main/div[2]/div[5]/div[1]/div[{}]/div[2]/div/div[1]/div[2]/div/div/a[1]'.format(i)

            # only one object contains the data, the other is None
            link_a = self.extract_link_by_xpath(xpath1)
            link_b = self.extract_link_by_xpath(xpath2)

            # it's either link_a exists or link_b
            if link_a or link_b:
                if link_a:
                    match_links.add(link_a)
                    continue
                else:
                    match_links.add(link_b)

        return match_links

    def extract_link_by_xpath(self, xpath):
        """
        Extract the links by the xpath
        """
        try:
            link = self.__webdriver__.find_element(By.XPATH, xpath).get_attribute('href')
            return link

        except:
            return False

    def get_books(self, links) -> set:
        full_books = []

        for link in links:
            print("Search at: ", link)
            books_in_this_link = self.open_page_parse_html_return_bookmaker_objects(link)
            full_books = full_books + books_in_this_link

        return set(full_books)

    def open_page_parse_html_return_bookmaker_objects(self, link):
        """
        This function opens the match result page, parses the document, and returns the tuple of
        (bookmaker, odds, time, game, team, etc)
        """
        self.__webdriver__.get(link)
        print('We wait 2 seconds')
        time.sleep(2)

        odds_in_page = []
            # Now we collect all bookmaker
        html_div_start_num = 2
        for j in range(html_div_start_num, 30):  # only first 10 bookmakers displayed
            try:
                # bookmaker xpath
                bookmaker = self.__webdriver__.find_element(By.XPATH,
                                                            '/html/body/div[1]/div/div[1]/div/main/div[2]/div[4]/div[1]/div/div[{}]/div[1]/a[2]/p'.format(
                                                                j)).text
                print(bookmaker)

                home = self.__webdriver__.find_element(By.XPATH,
                                                       '/html/body/div[1]/div/div[1]/div/main/div[2]/div[4]/div[1]/div/div[{}]/div[2]/div/div/p'.format(
                                                           j)).text
                print(home)

                # pay attention if it is home, away, draw, the xpath is different

                draw = self.__webdriver__.find_element(By.XPATH,
                                                       '/html/body/div[1]/div/div[1]/div/main/div[2]/div[4]/div[1]/div/div[{}]/div[3]/div/div/p'.format(
                                                           j)).text

                print(draw)
                away = self.__webdriver__.find_element(By.XPATH,
                                                       '/html/body/div[1]/div/div[1]/div/main/div[2]/div[4]/div[1]/div/div[{}]/div[4]/div/div/p'.format(
                                                           j)).text
                print(away)

                match = self.__webdriver__.find_element(By.XPATH,
                                                        '/html/body/div[1]/div/div[1]/div/main/div[2]/div[3]/div[1]').text  # match teams
                print(match)

                date = self.__webdriver__.find_element(By.XPATH,
                                                       # /html/body/div[1]/div/div[1]/div/main/div[2]/div[3]/div[2]/div[1]
                                                       # /html/body/div[1]/div/div[1]/div/main/div[2]/div[3]/div[2]/div[1]
                                                       '/html/body/div[1]/div/div[1]/div/main/div[2]/div[3]/div[2]/div[1]').text  # Date and time
                print(date)

                book = Bookmaker(sport='football', match=match, bookmaker=bookmaker, home=home, draw=draw, away=away,
                                 date=date)

                odds_in_page.append(book)
            except Exception as e:
                print(e)
                pass

        # TODO really pay attention to date time, and how to parse it, the format keeps change
        return odds_in_page
