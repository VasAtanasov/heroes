package bg.softuni.heroes;

import bg.softuni.heroes.data.enums.Slot;
import bg.softuni.heroes.data.models.Item;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@UtilityClass
public class DataSetUtils {

    public static List<Item> getItems() {
        return List.of(
                Item.builder().name("mhearne0").slot(Slot.PAULDRON).stamina(64).strength(13).attack(75).defence(87).build(),
                Item.builder().name("mannell1").slot(Slot.PADS).stamina(82).strength(28).attack(49).defence(88).build(),
                Item.builder().name("fconley2").slot(Slot.PADS).stamina(35).strength(80).attack(89).defence(78).build(),
                Item.builder().name("tfyndon3").slot(Slot.WEAPON).stamina(26).strength(84).attack(72).defence(94).build(),
                Item.builder().name("bkitchenman4").slot(Slot.HELMET).stamina(32).strength(48).attack(73).defence(2).build(),
                Item.builder().name("kstalman5").slot(Slot.PADS).stamina(8).strength(21).attack(44).defence(73).build(),
                Item.builder().name("lsaffill6").slot(Slot.GAUNTLETS).stamina(24).strength(18).attack(17).defence(81).build(),
                Item.builder().name("jcudd7").slot(Slot.PAULDRON).stamina(18).strength(22).attack(14).defence(98).build(),
                Item.builder().name("mgratrix8").slot(Slot.PADS).stamina(49).strength(3).attack(37).defence(55).build(),
                Item.builder().name("mlaffranconi9").slot(Slot.HELMET).stamina(37).strength(38).attack(61).defence(76).build(),
                Item.builder().name("hcreama").slot(Slot.HELMET).stamina(79).strength(3).attack(45).defence(11).build(),
                Item.builder().name("tsadlerb").slot(Slot.GAUNTLETS).stamina(22).strength(49).attack(1).defence(21).build(),
                Item.builder().name("divattsc").slot(Slot.HELMET).stamina(47).strength(71).attack(84).defence(36).build(),
                Item.builder().name("mstowelld").slot(Slot.HELMET).stamina(10).strength(41).attack(46).defence(61).build()
        );
    }

    public static Page<Item> getItemsPage() {
        return new PageImpl<>(getItems());
    }
}
