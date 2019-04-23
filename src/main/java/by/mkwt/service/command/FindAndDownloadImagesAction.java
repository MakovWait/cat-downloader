package by.mkwt.service.command;

import by.mkwt.entity.Image;
import by.mkwt.exception.IncorrectActionDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class FindAndDownloadImagesAction implements ImageAction {

    @Value("#{action['image_search_url']}")
    private String imageSearchUrl;

    @Value("#{action['downloaded_images_root']}")
    private String downloadedImagesRoot;

    @Value("#{action['user_agent']}")
    private String user_agent;

    @Value("#{action['required_parameter.limit']}")
    private String limit;

    @Value("#{exception['limit_is_not_specified']}")
    private String limitIsNotSpecified;

    private RestTemplate restTemplate;

    @Autowired
    public FindAndDownloadImagesAction(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> execute(Map<String, String> actionBody) {
        if (!actionBody.containsKey(limit)) {
            throw new IncorrectActionDataException(limitIsNotSpecified);
        }

        Image[] images = findImages(Integer.valueOf(actionBody.get(limit)));

        return downloadImages(images);
    }

    private Image[] findImages(int limit) {
        return restTemplate.getForObject(imageSearchUrl, Image[].class, limit);
    }

    private List<String> downloadImages(Image[] images) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", user_agent);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<String> paths = new ArrayList<>();

        for (Image image : images) {
            String url = image.getUrl();
            String imageName = url.substring(url.lastIndexOf('/') + 1);

            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

            try {
                Path testPath = Paths.get(downloadedImagesRoot + imageName);
                paths.add(testPath.toAbsolutePath().toString());
                Files.write(testPath, response.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return paths;
    }
}
