import com.rest.Controller.PeopleController;
import com.rest.Entity.People;
import com.rest.Repository.PeopleRepository;
import com.rest.services.PeopleServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
class MockitoPeopleControllerTest {

    // private static CyclicBuffer<People> peopleList;
    @InjectMocks
    PeopleController peopleController;
    @Mock
    PeopleServiceImpl peopleService;
    @Mock
    PeopleRepository peopleRepository;

    private static final String NAME = "Boo";
    private static final String EMAIL = "Boo@gmail.com";


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
    void withMockGetAllPeopleTest() {
        when(peopleService.findAllPeople()).thenReturn(peopleList);
        List<People> actual = peopleController.getAllPeople();
        assertArrayEquals(new List[]{peopleList}, new List[]{actual}, "Controller's test through the service mock");
    }


    @Test
    @DisplayName("getPeopleById")
    final void getPeopleByIdTest() {
        when(peopleService.findPeopleById(anyLong())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleById(1));

    }

    @Test
    @DisplayName("getPeopleByName")
    void getPeopleByNameTest() {
        when(peopleService.findPeopleByName(anyString())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleByName("Herring"));
    }

    @Test
    @DisplayName("getPeopleByEmail")
    void getPeopleByEmailTest() {
        when(peopleService.findPeopleByEmail(anyString())).thenReturn(people1);
        assertEquals(people1, peopleController.getPeopleByEmail("Herring@gmail.com"));
    }

    @Test
    @DisplayName("add new People")
    void newPeopleTest() {
        People newPeople = new People("Hagrid", "Hagrid@gmail.com");
        peopleList.add(newPeople);
        peopleController.newPeople("Hagrid", "Hagrid@gmail.com");
      //  when((peopleService.newGuyPeople(anyString(),anyString())).getId()).thenReturn(4L);
        assertEquals(newPeople, peopleController.getPeopleByName("Hagrid"));

    }

    @Test
    @DisplayName("getPeopleByName")
    void patchPeopleNameTest() {
    }

    @Test
    @DisplayName("getPeopleByName")
    void renewPeopleTest() {
    }

    @Test
    @DisplayName("getPeopleByName")
    void cleanPeopleByIdTest() {
    }

    @Test
    @DisplayName("getPeopleByName")
    void cleanAllPeopleTest() {
    }
}
