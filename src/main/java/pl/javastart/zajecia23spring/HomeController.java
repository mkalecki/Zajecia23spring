package pl.javastart.zajecia23spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory factory;

    @GetMapping("/")
//    @ResponseBody
    public String home(@RequestParam(required = false) String imie, Model model) {
        EntityManager entityManager = factory.createEntityManager();
        if (imie != null) {
            User user = new User();
            user.setName(imie);
            user.setCreationDate(new Date());

            entityManager.getTransaction().begin();
            entityManager.persist(user);

            entityManager.getTransaction().commit();
        }

        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);

        List<User> users = query.getResultList();

        model.addAttribute("users", users);

//            String result = "";
//            for (User u : users) {
//                result += u.getName() + "<br>";
//            }

        entityManager.close();
        return "home";
////            return result;
//        } else
//            return "nie dodano";


    }
}

