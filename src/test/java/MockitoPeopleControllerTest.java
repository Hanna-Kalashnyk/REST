import com.rest.Controller.PeopleController;
import com.rest.Entity.People;
import com.rest.services.PeopleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class MockitoPeopleControllerTest {

    @InjectMocks
    PeopleController peopleController;
    @Mock
    PeopleServiceImpl peopleService;

    List<People> peopleList = new ArrayList<>();
    People people1;
    People people2;
    People people3;
    People newPeople;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this); // Initialize according to annotations

        people1 = new People(1, "Herring", "Herring@gmail.com");
        people2 = new People(2, "Malcolm", "Malcolm@gmail.com");
        people3 = new People(3, "Jack", null);
        peopleList.add(people1);
        peopleList.add(people2);
        peopleList.add(people3);
        newPeople = new People(4, "Hagrid", "Hagrid@gmail.com");
    }

    @Test
    @DisplayName("getAllPeople Test")
    void withMockGetAllPeopleTest() {
        when(peopleService.findAllPeople()).thenReturn(peopleList);
        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "Controller's test through the service mock");
    }


    @Test
    @DisplayName("getPeopleById Test")
    final void getPeopleByIdTest() {
        when(peopleService.findPeopleById(anyLong())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleById(1));

    }

    @Test
    @DisplayName("getPeopleByName Test")
    void getPeopleByNameTest() {
        when(peopleService.findPeopleByName(anyString())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleByName("Herring"));
    }

    @Test
    @DisplayName("getPeopleByEmail Test")
    void getPeopleByEmailTest() {
        when(peopleService.findPeopleByEmail(anyString())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleByEmail("Herring@gmail.com"));
    }

    @Test
    @DisplayName("add new People Test")
    void newPeopleTest() {
        peopleList.add(newPeople);
        when(peopleService.findAllPeople()).thenReturn(peopleList);
        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual},"List check uot after people addition");


        when(peopleService.findPeopleByName(anyString())).thenReturn(newPeople);
        long actualId = peopleController.getPeopleByName("Hagrid").getId();
        assertEquals(4L, actualId);

        when(peopleService.findPeopleByEmail(anyString())).thenReturn(newPeople);
        assertEquals(newPeople, peopleController.getPeopleByEmail("Hagrid@gmail.com"));
    }

    @Test
    @DisplayName("patch newPeople's name to Giant Test")
    void changePeopleNameTest() {
        newPeople.setName("Giant");

        when(peopleService.findPeopleByEmail(anyString())).thenReturn(newPeople);
        when(peopleService.findPeopleById(anyLong())).thenReturn(newPeople);

        assertEquals(newPeople, peopleController.getPeopleByEmail("Herring@gmail.com"));
        assertEquals(newPeople, peopleController.getPeopleById(4));

        String actualName = peopleController.getPeopleById(4).getName();
        assertEquals("Giant", actualName);

    }

    @Test
    @DisplayName("PUT people id=4 Test")
    void renewPeopleTest() {
        newPeople.setName("Giant");
        newPeople.setEmail("Giant@gmail.com");

        when(peopleService.findPeopleById(anyLong())).thenReturn(newPeople);
        assertEquals(newPeople, peopleController.getPeopleById(4));

        String actualEmail = peopleController.getPeopleById(4).getEmail();
        assertEquals("Giant@gmail.com", actualEmail);


        String actualName = peopleController.getPeopleById(4).getName();
        assertEquals("Giant", actualName);
    }

    @Test
    @DisplayName("Delete People by id Test")
    void cleanPeopleByIdTest() {
        peopleList.remove(people3);

        when(peopleService.findAllPeople()).thenReturn(peopleList);
        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual},"List check out after people(id=3) deletion");

        when(peopleService.findPeopleById(anyLong())).thenReturn(null);
        assertNull(peopleController.getPeopleById(3));


        when(peopleService.findPeopleByName(anyString())).thenReturn(null);
        assertNull(peopleController.getPeopleByName("Jack"));
    }

    @Test
    @DisplayName("Delete All People Test")
    void cleanAllPeopleTest() {
       peopleList.remove(people1);
       peopleList.remove(people2);
       peopleList.remove(people3);


        when(peopleService.findAllPeople()).thenReturn(peopleList);
        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual});

        when(peopleService.findPeopleById(anyLong())).thenReturn(null);
        assertNull(peopleController.getPeopleById(3));
        assertNull(peopleController.getPeopleById(2));
        assertNull(peopleController.getPeopleById(1));


        when(peopleService.findPeopleByName(anyString())).thenReturn(null);
        assertNull(peopleController.getPeopleByName("Jack"));

        when(peopleService.findPeopleByEmail(anyString())).thenReturn(null);
        assertNull(peopleController.getPeopleByName("Malcolm@gmail.com"));

    }


}
