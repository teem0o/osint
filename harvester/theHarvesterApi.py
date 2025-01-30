from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/harvest', methods=['GET'])
def harvest():
    domain = request.args.get('domain')
    source = request.args.get('source')
    if not domain:
        return jsonify({"error": "Domain parameter is required"}), 400

    try:
        result = subprocess.run(
            ["python3", "theHarvester.py", "-d", domain, "-b", source],
            capture_output=True,
            text=True,
            cwd="/app"
        )

        if result.returncode != 0:
            return jsonify({"error": result.stderr}), 500

        return result.stdout, 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)