# Transcribe CLI

A simple command-line tool to transcribe audio files using **faster-whisper**.

---

# Prerequisites

* Python 3.9+
* Homebrew (recommended for macOS)
* pipx (for global CLI installation)

---

# 1. Install pipx

```bash
brew install pipx
pipx ensurepath
source ~/.zshrc
```

Verify:

```bash
pipx --version
```

---

# 2. Project Structure

```
transcribe/
│
├── pyproject.toml
└── transcribe.py
```

---

# 3. transcribe.py

```python
import argparse
from faster_whisper import WhisperModel

def main():
    parser = argparse.ArgumentParser(description="Transcribe audio file")
    parser.add_argument("file", help="Path to audio file")
    parser.add_argument("--model", default="base", help="Model size (tiny, base, small, medium, large)")
    parser.add_argument("--output", help="Optional output file")

    args = parser.parse_args()

    model = WhisperModel(args.model, compute_type="int8")

    segments, info = model.transcribe(args.file)

    text = []
    for segment in segments:
        text.append(segment.text)

    result = " ".join(text)

    if args.output:
        with open(args.output, "w") as f:
            f.write(result)
    else:
        print(result)
```

---

# 4. pyproject.toml

```toml
[project]
name = "transcribe-cli"
version = "0.1.0"
dependencies = [
    "faster-whisper"
]

[project.scripts]
transcribe = "transcribe:main"
```

---

# 5. Install CLI globally

Navigate to project directory:

```bash
cd /path/to/transcribe
pipx install .
```

Verify installation:

```bash
pipx list
```

---

# 6. Usage

Basic transcription:

```bash
transcribe audio1.m4a
```

Specify model:

```bash
transcribe audio1.m4a --model small
```

Save output to file:

```bash
transcribe audio1.m4a --output result.txt
```

---

# 7. Available models

| Model  | Speed    | Accuracy |
| ------ | -------- | -------- |
| tiny   | fastest  | lowest   |
| base   | fast     | good     |
| small  | balanced | better   |
| medium | slower   | high     |
| large  | slowest  | best     |

Example:

```bash
transcribe meeting.m4a --model medium
```

---

# 8. Update after code changes

```bash
pipx reinstall transcribe-cli
```

or

```bash
pipx install -e .
```

---

# 9. Uninstall

```bash
pipx uninstall transcribe-cli
```

---

# 10. Installed locations (for reference)

| Item        | Path                               |
| ----------- | ---------------------------------- |
| virtual env | ~/.local/pipx/venvs/transcribe-cli |
| executable  | ~/.local/bin/transcribe            |

Ensure PATH contains:

```bash
export PATH="$HOME/.local/bin:$PATH"
```

---
