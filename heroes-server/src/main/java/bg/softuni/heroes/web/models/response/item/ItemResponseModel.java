package bg.softuni.heroes.web.models.response.item;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseModel {

    private long total;
    private List<? extends ItemListModel> items;
}
