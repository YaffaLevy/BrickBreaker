package aws;

import basicneuralnetwork.NeuralNetwork;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class BrickBreakerRequestHandler implements RequestHandler<BrickBreakerRequestHandler.BrickBreakerRequest,
        BrickBreakerRequestHandler.BrickBreakerResponse>
{
    private final S3Client s3Client;

    public BrickBreakerRequestHandler()
    {
        s3Client = S3Client.create();
    }

    @Override
    public BrickBreakerResponse handleRequest(BrickBreakerRequest request, Context context)
    {
        try
        {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("reiff.bbnn")
                    .key("BestNW.json")
                    .build();
            double guess[] = new double[4];

            guess[0] = request.xball; // x ball
            guess[1] = request.xpaddle; //x paddle
            guess[2] = request.xbrick; // x brick
            guess[3] = request.ybrick; //y brick

            InputStream in = s3Client.getObject(getObjectRequest);
            NeuralNetwork network = NeuralNetwork.readFromFile(in);
            double[] output = network.guess(guess);
            BrickBreakerResponse response = new BrickBreakerResponse(output[0], output[1], "");
            return response;

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }


    record BrickBreakerRequest(
            double xball,
            double xpaddle,
            double xbrick,
            double ybrick
    )

    {
    }

    record BrickBreakerResponse(
            double right,
            double left,
            String move)
    {
        public String moveString()
        {
            if (right > left)
            {
                return "RIGHT";
            } else
            {
                return "LEFT";
            }
        }
    }
}


