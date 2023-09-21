from flask import Flask, render_template, request
import os

app = Flask(__name__)
@app.route('/')
def index():
    return render_template('index.html')

@app.route('/pp', methods=['GET'])
def play():
    os.system('adb -s 192.168.1.161:5555 shell  input keyevent KEYCODE_MEDIA_PLAY_PAUSE')
    # from pymsgbox import alert
    # alert('பாருங்க பாருங்க')
    return 'success'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5050)