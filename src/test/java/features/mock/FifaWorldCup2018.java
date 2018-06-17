package features.mock;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

public class FifaWorldCup2018 {

    public Response response;

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://raw.githubusercontent.com/openfootball/world-cup.json/master/2018/worldcup.json";
        response = get();

    }

    @Test
    public void getAllRoundMatches() {
        response.then().log().all();
        List<Integer> a = response.jsonPath().get("rounds.matches[-6].num");

        Assert.assertEquals(a.get(a.size() - 1) == 48, true);
    }

    @Test
    public void liveScore() {
        List matches = response.jsonPath().get("rounds.matches");
        System.out.format("%84s\n", StringUtils.center("**************************************", 84));
        System.out.format("%84s\n", StringUtils.center("FIFA WORLD CUP 2018 RUSSIA", 84));
        System.out.format("%84s\n", StringUtils.center("**************************************", 84));
        for (int day = 0; day < matches.size(); day++) {
            List<String> team1 = response.jsonPath().get(String.format("rounds.matches[%d].team1.name", day));
            List<Integer> score1 = response.jsonPath().get(String.format("rounds.matches[%d].score1", day));
            List<String> team2 = response.jsonPath().get(String.format("rounds.matches[%d].team2.name", day));
            List<Integer> score2 = response.jsonPath().get(String.format("rounds.matches[%d].score2", day));
            for (int i = 0; i < team1.size(); i++) {
                System.out.format("%32s %10d : %-10d %-32s\n", team1.get(i), score1.get(i), score2.get(i), team2.get(i));
            }
        }
    }

    @Test
    public void getAllStadiums() {
        List<List<String>> stadiums = response.jsonPath().get("rounds.matches.stadium.name");
        List<String> FF18Stadium = new ArrayList<>();
        for (List<String> matches : stadiums) {
            for (String stadium : matches) {
                FF18Stadium.add(stadium);
                System.out.println(stadium);
            }
        }

        System.out.println(FF18Stadium);

    }

    @DataProvider(name = "team")
    public static Object[][] team() {

        return new Object[][]{
                {"Group A", "Russia", "Egypt", "Uruguay", "Saudi Arabia"},
                {"Group B", "Portugal", "Morocco", "Iran", "Spain"},
                {"Group C", "France", "Peru", "Denmark", "Australia"},
                {"Group D", "Argentina", "Croatia", "Nigeria", "Iceland"},
                {"Group E", "Brazil", "Costa Rica", "Serbia", "Switzerland"},
                {"Group F", "Germany", "Sweden", "South Korea", "Mexico"},
                {"Group G", "Belgium", "Tunisia", "England", "Panama"},
                {"Group H", "Poland", "Colombia", "Japan", "Senegal"},

        };

    }

    @Test(dataProvider = "team")
    public void groupTest(String group, String team1, String team2, String team3, String team4) {
        List<List<String>> teams = response.jsonPath().getList("rounds.matches.team1.name");
        List<List<String>> groupAMatches = response.jsonPath().get("rounds.matches.group");

        ArrayList<String> groupA = new ArrayList();

        for (int i = 0; i < groupAMatches.size(); i++) {
            for (int j = 0; j < groupAMatches.get(i).size(); j++)
                if (groupAMatches.get(i).get(j).equalsIgnoreCase(group)) {
                    if (i < 1) {
                        groupA.add(teams.get(i).get(j));
                    } else {
                        if (!isContains(groupA, teams.get(i).get(j))) {
                            groupA.add(teams.get(i).get(j));
                        }
                    }

                }
        }
        Assert.assertEquals(groupA.toArray(), new String[]{team1, team2, team3, team4});
    }

    @DataProvider(name = "stadium")
    public static Object[][] stadium() {

        return new Object[][]{
                {
                        "Luzhniki Stadium",
                        "Ekaterinburg Arena",
                        "Fisht Stadium",
                        "Saint Petersburg Stadium",
                        "Kazan Arena",
                        "Mordovia Arena",
                        "Spartak Stadium",
                        "Kaliningrad Stadium",
                        "Rostov Arena",
                        "Samara Arena",
                        "Nizhny Novgorod Stadium",
                        "Volgograd Arena"
                }
        };
    }

    @Test(dataProvider = "stadium")
    public void stadiumTest(String stadium1,
                            String stadium2,
                            String stadium3,
                            String stadium4,
                            String stadium5,
                            String stadium6,
                            String stadium7,
                            String stadium8,
                            String stadium9,
                            String stadium10,
                            String stadium11,
                            String stadium12) {
        List<List<String>> stadiums = response.jsonPath().getList("rounds.matches.stadium.name");
        ArrayList<String> actual = new ArrayList<>();
        for (List<String> items : stadiums) {
            for (String stadium : items) {
                if (!isContains(actual, stadium)) {
                    actual.add(stadium);
                }
            }
        }

        Assert.assertEquals(actual.size(), 12);
        Assert.assertEquals(actual.toArray(), new String[]{stadium1, stadium2, stadium3, stadium4, stadium5, stadium6, stadium7, stadium8, stadium9, stadium10, stadium11, stadium12});

    }


    private Boolean isContains(ArrayList<String> array, String findItem) {
        Boolean isContains = false;
        for (String item : array) {
            if (item.equalsIgnoreCase(findItem)) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }
}
