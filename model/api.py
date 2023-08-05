from flask import Flask, request
from joblib import load
import pickle

app = Flask(__name__)

model = load("model.pkl")

@app.route('/')
def home():
    return "Hi, if you want to predict, use /predict endpoint"


@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        home = data.get("home")
        home = int(home)
        draw = data.get("draw")
        draw = int(draw)
        away = data.get("away")
        away = int(away)
        home_elo = data.get("home_elo")
        home_elo = int(home_elo)
        away_elo = data.get("away_elo")
        away_elo = int(away_elo)

        print(data)
        y_hat = model.predict([[home, draw, away, home_elo, away_elo]])

        """
        return the winrate will be enough, cuz the home team and away team data is in Java container
        """
        return str(y_hat)

    except ValueError as e:
        return str(e)



if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
