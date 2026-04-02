import argparse
from faster_whisper import WhisperModel

def main():
    parser = argparse.ArgumentParser(description="Transcribe audio")
    parser.add_argument("file", help="Audio file path")

    args = parser.parse_args()

    model = WhisperModel("base", compute_type="int8")
    segments, info = model.transcribe(args.file)

    for segment in segments:
        print(segment.text)
