package ru.job4j.todolist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ToDoServlet extends HttpServlet {
    private final SessionFactory sf = new Configuration().configure("postgresql.cfg.xml").buildSessionFactory();
    private final Actions dao = Actions.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("items", dao.findAll(Item.class, sf, "created DESC"));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/views/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String operation = req.getParameter("op");
        String res;
        ObjectMapper mapper = new ObjectMapper();
        User user = (User) req.getSession().getAttribute("current");
        if ("add".equals(operation)) {
            Item added = dao.add(Item.of(req.getParameter("descr"), user), sf);
            added.getUser().setPassword(null);
            res = mapper.writeValueAsString(added);
        } else {
            Item updated = dao.findById(Integer.valueOf(req.getParameter("id")), Item.class, sf);
            updated.setDone(Boolean.parseBoolean(req.getParameter("isDone")));
            updated.getUser().setPassword(null);
            dao.update(updated, sf);
            res = mapper.writeValueAsString(updated);
        }
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.println(res);
            writer.flush();
        }
    }
}
