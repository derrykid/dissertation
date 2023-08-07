from flask import Flask, request
from joblib import load
import pickle

app = Flask(__name__)

model = load("regmodel.pkl")

@app.route('/')
def home():
    return "Hi, if you want to predict, use /predict endpoint"


@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        home = data.get("homeProbability")
        # home = int(home)
        draw = data.get("drawProbability")
        # draw = int(draw)
        away = data.get("awayProbability")
        # away = int(away)

        print(data)
        y_hat = model.predict([[draw, away]])

        """
        return the winrate will be enough, cuz the home team and away team data is in Java container
        """
        return str(y_hat[0])

    except ValueError as e:
        return str(e)



if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
