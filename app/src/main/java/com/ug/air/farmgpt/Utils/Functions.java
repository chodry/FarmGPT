package com.ug.air.farmgpt.Utils;

import android.content.Context;
import android.widget.Toast;

import com.ug.air.farmgpt.Activities.ChatActivity;
import com.ug.air.farmgpt.Api.ApiClient;
import com.ug.air.farmgpt.Api.JsonPlaceHolder;
import com.ug.air.farmgpt.R;

import java.util.List;

public class Functions {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static JsonPlaceHolder jsonPlaceHolder(String value) {
        JsonPlaceHolder jsonPlaceHolder;

        if (value.equals("account")){
            jsonPlaceHolder = ApiClient.getClientAccount().create(JsonPlaceHolder.class);
        }
        else {
            jsonPlaceHolder = ApiClient.getClientChat().create(JsonPlaceHolder.class);
        }

        return jsonPlaceHolder;
    }

    public static String convertListToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : list) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }

    public static String[] get_items(Context context, String topic){

        String[] items = null;

        switch (topic) {
            case "Soil Preparation": {
                items = new String[]{context.getString(R.string.soil1), context.getString(R.string.soil2),
                        context.getString(R.string.soil3), context.getString(R.string.soil4),
                        context.getString(R.string.soil5), context.getString(R.string.soil6)};

                break;
            }
            case "Planting": {
                items = new String[]{context.getString(R.string.planting1), context.getString(R.string.planting2),
                        context.getString(R.string.planting3), context.getString(R.string.planting4),
                        context.getString(R.string.planting5), context.getString(R.string.planting6), context.getString(R.string.planting7)};

                break;
            }
            case "Crop Management": {
                items = new String[]{context.getString(R.string.management1), context.getString(R.string.management2),
                        context.getString(R.string.management3), context.getString(R.string.management4),
                        context.getString(R.string.management5), context.getString(R.string.management6),
                        context.getString(R.string.management7), context.getString(R.string.management8)};

                break;
            }
            case "Harvesting": {
                items = new String[]{context.getString(R.string.harvesting1), context.getString(R.string.harvesting2),
                        context.getString(R.string.harvesting3), context.getString(R.string.harvesting4),
                        context.getString(R.string.harvesting5), context.getString(R.string.harvesting6)};

                break;
            }
            case "Marketing and Sales": {
                items = new String[]{context.getString(R.string.market1), context.getString(R.string.market2),
                        context.getString(R.string.market3), context.getString(R.string.market4),
                        context.getString(R.string.market5), context.getString(R.string.market6),
                        context.getString(R.string.market7)};

                break;
            }
            case "Crop Diversity": {
                items = new String[]{context.getString(R.string.crop1), context.getString(R.string.crop2),
                        context.getString(R.string.crop3), context.getString(R.string.crop4),
                        context.getString(R.string.crop5), context.getString(R.string.crop6),
                        context.getString(R.string.crop7), context.getString(R.string.crop8)};

                break;
            }
            case "Genetically Modified Crops (GMOs)": {
                items = new String[]{context.getString(R.string.genetically1), context.getString(R.string.genetically2),
                        context.getString(R.string.genetically3), context.getString(R.string.genetically4),
                        context.getString(R.string.genetically5), context.getString(R.string.genetically6)};

                break;
            }
            case "Integrated Pest Management (IPM)": {
                items = new String[]{context.getString(R.string.integrated1), context.getString(R.string.integrated2),
                        context.getString(R.string.integrated3), context.getString(R.string.integrated4),
                        context.getString(R.string.integrated5), context.getString(R.string.integrated6),
                        context.getString(R.string.integrated7), context.getString(R.string.integrated8)};

                break;
            }
            case "Climate and Weather Impacts on Crop Farming": {
                items = new String[]{context.getString(R.string.climate1), context.getString(R.string.climate2),
                        context.getString(R.string.climate3), context.getString(R.string.climate4),
                        context.getString(R.string.climate5), context.getString(R.string.climate6)};

                break;
            }
            case "Sustainable Agriculture": {
                items = new String[]{context.getString(R.string.sustainable1), context.getString(R.string.sustainable2),
                        context.getString(R.string.sustainable3), context.getString(R.string.sustainable4),
                        context.getString(R.string.sustainable5), context.getString(R.string.sustainable6)};

                break;
            }
            case "Equipment and Technology": {
                items = new String[]{context.getString(R.string.equipment1), context.getString(R.string.equipment2),
                        context.getString(R.string.equipment3), context.getString(R.string.equipment4),
                        context.getString(R.string.equipment5), context.getString(R.string.equipment6)};

                break;
            }
            case "Government Policies and Programs": {
                items = new String[]{context.getString(R.string.government1), context.getString(R.string.government2),
                        context.getString(R.string.government3), context.getString(R.string.government4),
                        context.getString(R.string.government5), context.getString(R.string.government6)};

                break;
            }

            case "Livestock selection and breeding": {
                items = new String[]{context.getString(R.string.livestock1), context.getString(R.string.livestock2),
                        context.getString(R.string.livestock3), context.getString(R.string.livestock4),
                        context.getString(R.string.livestock5), context.getString(R.string.livestock6)};

                break;
            }
            case "Animal housing and facilities": {
                items = new String[]{context.getString(R.string.housing1), context.getString(R.string.housing2),
                        context.getString(R.string.housing3), context.getString(R.string.housing4),
                        context.getString(R.string.housing5), context.getString(R.string.housing6)};

                break;
            }
            case "Feed and nutrition": {
                items = new String[]{context.getString(R.string.feed1), context.getString(R.string.feed2),
                        context.getString(R.string.feed3), context.getString(R.string.feed4),
                        context.getString(R.string.feed5), context.getString(R.string.feed6)};

                break;
            }
            case "Health and disease management": {
                items = new String[]{context.getString(R.string.health1), context.getString(R.string.health2),
                        context.getString(R.string.health3), context.getString(R.string.health4),
                        context.getString(R.string.health5), context.getString(R.string.health6)};

                break;
            }
            case "Reproduction and birthing": {
                items = new String[]{context.getString(R.string.birthing1), context.getString(R.string.birthing2),
                        context.getString(R.string.birthing3), context.getString(R.string.birthing4),
                        context.getString(R.string.birthing5), context.getString(R.string.birthing6)};

                break;
            }
            case "Pasture and grazing management": {
                items = new String[]{context.getString(R.string.pasture1), context.getString(R.string.pasture2),
                        context.getString(R.string.pasture3), context.getString(R.string.pasture4),
                        context.getString(R.string.pasture5), context.getString(R.string.pasture6)};

                break;
            }
            case "Handling and husbandry practices": {
                items = new String[]{context.getString(R.string.handling1), context.getString(R.string.handling2),
                        context.getString(R.string.handling3), context.getString(R.string.handling4),
                        context.getString(R.string.handling5), context.getString(R.string.handling6)};

                break;
            }
            case "Marketing and sales": {
                items = new String[]{context.getString(R.string.sales1), context.getString(R.string.sales2),
                        context.getString(R.string.sales3), context.getString(R.string.sales4),
                        context.getString(R.string.sales5), context.getString(R.string.sales6)};

                break;
            }
            case "Technology in animal farming": {
                items = new String[]{context.getString(R.string.technology1), context.getString(R.string.technology2),
                        context.getString(R.string.technology3), context.getString(R.string.technology4),
                        context.getString(R.string.technology5), context.getString(R.string.technology6)};

                break;
            }
            case "Sustainable animal farming practices": {
                items = new String[]{context.getString(R.string.farming1), context.getString(R.string.farming2),
                        context.getString(R.string.farming3), context.getString(R.string.farming4),
                        context.getString(R.string.farming5), context.getString(R.string.farming6)};

                break;
            }
            case "Animal transportation and handling": {
                items = new String[]{context.getString(R.string.animal1), context.getString(R.string.animal2),
                        context.getString(R.string.animal3), context.getString(R.string.animal4),
                        context.getString(R.string.animal5), context.getString(R.string.animal6)};

                break;
            }
            case "Business management for animal farming": {
                items = new String[]{context.getString(R.string.business1), context.getString(R.string.business2),
                        context.getString(R.string.business3), context.getString(R.string.business4),
                        context.getString(R.string.business5), context.getString(R.string.business6)};

                break;
            }

        }

        return items;

    }

    public static int countWordsInSentence(String sentence) {
        if (sentence == null || sentence.trim().isEmpty()) {
            return 0;
        }

        // Split the sentence into words using space as the delimiter
        String[] words = sentence.trim().split("\\s+");

        // Return the number of words
        return words.length;
    }

}
