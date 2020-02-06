import com.rest.Controller.PeopleController;
import com.rest.Entity.People;
import com.rest.Repository.PeopleRepository;
import com.rest.services.PeopleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PeopleController.class)
class PeopleControllerTest<peopleController> {

    // private static CyclicBuffer<People> peopleList;

    @InjectMocks
    PeopleController peopleController;
    @Mock
    PeopleRepository peopleRepository;
    @Mock
    PeopleServiceImpl peopleService;

    List<People> peopleList = new ArrayList<>();
    People people1;
    People people2;
    People people3;
    People newPeople;
    People hugrid;

    private static final String NAME = "Hagrid";
    private static final String EMAIL = "Hagrid@gmail.com";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Initialize according to annotations

        people1 = new People(1, "Herring", "Herring@gmail.com");
        people2 = new People(2, "Malcolm", "Malcolm@gmail.com");
        people3 = new People(3, "Jack", null);
        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
    }

    @Test
    @DisplayName("getAllPeopleTest")
    void gggetAllPeopleTest() throws Exception {


        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "Controller's test through the service mock");
    }


    @Test
    final void getPeopleByIdTest() {

        People people1 = new People(4, "Herring", "Herring@gmail.com");
        // when(peopleService.findPeopleById(anyInt())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleById(1));

    }

    @Test
    void getPeopleByName() {
    }

    @Test
    void getPeopleByEmailTest() {
    }

    @Test
    @DisplayName("add new People")
    void newPeopleTest() {
        People newPeople = new People(4, "Hagrid", "Hagrid@gmail.com");
//        peopleList.add(newPeople);
//        when((peopleService.findPeopleByName(anyString())).getId()).thenReturn(4L);
//        assertEquals(newPeople, peopleController.getPeopleByName("Hagrid"));


       // peopleList.add(newPeople);
       // then(this.peopleRepository).should().save(argThat(peopleMatchesFields()));
    }

    private ArgumentMatcher<People> peopleMatchesFields() {

        return people -> NAME.equals(newPeople.getName()) && EMAIL.equals(newPeople.getEmail());
    }


    @Test
    void changePeopleNameTest() {
    }

    @Test
    void renewPeopleTest() {
    }

    @Test
    void cleanPeopleByIdTest() {
    }

    @Test
    void cleanAllPeopleTest() {
    }
}