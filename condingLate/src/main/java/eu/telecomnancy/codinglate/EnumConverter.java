package eu.telecomnancy.codinglate;

import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCategory;
import eu.telecomnancy.codinglate.database.dataObject.enums.ProductCondition;

public class EnumConverter {

    public static ProductCategory convertCategoryToInt(String category) {
        if (category == null) {
            return null;
        }

        // Convertir la catégorie en minuscules pour la comparaison insensible à la casse
        String lowercaseCategory = category.toLowerCase();

        return switch (lowercaseCategory) {
            case "auto" -> ProductCategory.AUTO;
            case "jardin" -> ProductCategory.GARDEN;
            case "maison" -> ProductCategory.HOME;
            case "multimedia" -> ProductCategory.MULTIMEDIA;
            case "sport" -> ProductCategory.SPORT;
            case "autre" -> ProductCategory.OTHER;
            default -> null;
        };
    }

    public static ProductCondition convertConditionToInt(String condition) {
        if (condition == null) {
            return null;
        }

        // Convertir la condition en minuscules pour la comparaison insensible à la casse
        String lowercaseCondition = condition.toLowerCase();

        return switch (lowercaseCondition) {
            case "neuf" -> ProductCondition.NEW;
            case "bon" -> ProductCondition.GOOD;
            case "reconditionné" -> ProductCondition.USED;
            case "utilisé" -> ProductCondition.REFURBISHED;
            default -> null;
        };
    }

}
