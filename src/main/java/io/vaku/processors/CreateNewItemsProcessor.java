package io.vaku.processors;

import com.google.gson.Gson;
import io.vaku.HttpRequest;
import io.vaku.app.Item;
import io.vaku.app.ItemsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewItemsProcessor implements RequestProcessor {
    private ItemsRepository itemsRepository;

    public CreateNewItemsProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = itemsRepository.save(gson.fromJson(request.getBody(), Item.class));

        String response = "" +
                "HTTP/1.1 201 Created\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                gson.toJson(item);
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
