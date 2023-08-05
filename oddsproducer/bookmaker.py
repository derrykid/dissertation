from datetime import datetime


class Bookmaker:

    def __init__(self, sport, match, bookmaker, home, draw, away, date):
        self.sport = sport
        self.bookmaker = bookmaker
        parts = match.split('–')
        parts = [e.strip() for e in parts]
        self.home_team = parts[0]
        self.away_team = parts[-1]
        self.home_odd = float(home)
        self.draw_odd = float(draw)
        self.away_odd = float(away)

        """
        date format:
        
        Saturday,
        12 Aug 2023,
        14:00
        """

        date = date.replace('\n', '')

        date_parts = ",".join(date.split(",")[1:]).strip()

        dt = datetime.strptime(date_parts, '%d %b %Y,%H:%M')
        self.match_time = dt.strftime("%Y-%m-%d %H:%M:%S")

        try:
            self.homeProbability = round(1 / self.home_odd, 4)
            self.drawProbability = round(1 / self.draw_odd, 4)
            self.awayProbability = round(1 / self.away_odd, 4)

        except ZeroDivisionError as e :
            self.homeProbability = 0
            self.drawProbability = 0
            self.awayProbability = 0


    def __str__(self):
        return self.__dict__