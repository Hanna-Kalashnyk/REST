
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import ch.qos.logback.core.helpers.CyclicBuffer;
import com.rest.Controller.PeopleController;
import com.rest.Entity.People;
import com.rest.services.PeopleServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PeopleController.class)
class PeopleControllerTest<peopleController> {

    // private static CyclicBuffer<People> peopleList;


    PeopleController peopleController;

    PeopleServiceImpl peopleService;

    List<People> peopleList = new ArrayList<>();
    People people1;
    People people2;
    People people3;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Initialize according to annotations

        people1 = new People("Herring", "Herring@gmail.com");
        people2 = new People("Malcolm", "Malcolm@gmail.com");
        people3 = new People("Jack", null);
        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
    }

    @Test
    @DisplayName("getAllPeopleTest")
    void gggetAllPeopleTest() {

        People people1 = new People("Herring", "Herring@gmail.com");
        People people2 = new People("Malcolm", "Malcolm@gmail.com");
        People people3 = new People("Jack", null);
        List<People> peopleList = new ArrayList<People>();

        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
        //  when(peopleService.findAllPeople()).thenReturn(peopleList);


        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "Controller's test through the service mock");
    }

//    private void assertArrayEquals(List<People> peopleList, List<People> actual, String s)
//
//    @Test
//    @DisplayName("getAllPeopleTest")
//    void getAllPeopleTest() throws Exception {
//
//    People people1 = new People(1, "Herring", "Herring@gmail.com");
//    People people2 =  new People(2, "Malcolm", "Malcolm@gmail.com");
//    People people3 =  new People(3, "Jack", null);
//        assertArrayEquals(new List[]{(List) people1, (List) people2, (List) people3}, new List[]{peopleController.getAllPeople()}, "If Are all People got well from the Poeple DATAbase");
//    }

    @Test
    final void getPeopleByIdTest() {

        People people1 = new People("Herring", "Herring@gmail.com");
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
        People newPeople = new People("Hagrid", "Hagrid@gmail.com");
        peopleList.add(newPeople);
        when((peopleService.findPeopleByName(anyString())).getId()).thenReturn(4L);
        assertEquals(newPeople, peopleController.getPeopleByName("Hagrid"));
    }

    @Test
    void patchPeopleNameTest() {
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