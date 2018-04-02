package org.edu.energycourse.mc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @RequestMapping("/contract")
    public String showContractForm ()
    {
        return getModulePage("form");
    }

    protected String getModulePath()
    {
        return "modules/student";
    }
}
