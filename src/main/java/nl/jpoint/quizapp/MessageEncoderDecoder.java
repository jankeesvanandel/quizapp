package nl.jpoint.quizapp;

import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class MessageEncoderDecoder implements Encoder<QuizQuestion, String>, Decoder<String, QuizQuestion>{

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public QuizQuestion decode(String s) {
        try{
            return mapper.readValue(s, QuizQuestion.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encode(QuizQuestion message) {
        try{
            return mapper.writeValueAsString(message);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
