from flask import Flask, render_template, request
import os

app = Flask(__name__)
@app.route('/')
def index():
    return render_template('index.html')

@app.route('/play', methods=['GET'])
def play():
    os.system('adb -s 192.168.1.161:5555 shell  input keyevent KEYCODE_MEDIA_PLAY')
    return 'success'

@app.route('/pause', methods=['GET'])
def pause():
    os.system('adb -s 192.168.1.161:5555 shell  input keyevent KEYCODE_MEDIA_PAUSE')
    return 'success'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5050)