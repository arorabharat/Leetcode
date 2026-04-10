import fs from "fs";
import OpenAI from "openai";

const openai = new OpenAI();

const response = await openai.audio.transcriptions.create({
    file: fs.createReadStream("audio.wav"),
    model: "gpt-4o-transcribe"
});

console.log(response.text);