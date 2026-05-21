package com.my.ox_quiz.controller;

import com.my.ox_quiz.domain.Member;
import com.my.ox_quiz.dto.QuizDto;
import com.my.ox_quiz.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quiz")
    public String list(Model model) {
        List<QuizDto> quizzes = quizService.findAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quiz/list";
    }

    @PostMapping("/quiz/insert")
    public String insert(@ModelAttribute QuizDto quizDto, Model model) {
        try {
            quizService.createQuiz(quizDto);
            return "redirect:/quiz";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("quizzes", quizService.findAllQuizzes());
            return "quiz/list";
        }
    }


    @GetMapping("/quiz/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        QuizDto quiz = quizService.findQuizById(id);
        model.addAttribute("quiz", quiz);
        return "quiz/update";
    }

    @PostMapping("/quiz/update")
    public String update(@ModelAttribute QuizDto quizDto, Model model) {
        try {
            quizService.updateQuiz(quizDto);
            return "redirect:/quiz";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "quiz/update";
        }
    }

    @PostMapping("/quiz/delete")
    public String delete(@RequestParam Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz";
    }



    @GetMapping("/quiz/play")
    public String play(Model model) {
        try {
            QuizDto randomQuiz = quizService.getRandomQuiz();
            model.addAttribute("quiz", randomQuiz);
            return "quiz/play";
        } catch (IllegalStateException e) {

            model.addAttribute("noQuizMessage", e.getMessage());
            return "quiz/play";
        }
    }

    @PostMapping("/quiz/check")
    public String checkAnswer(@RequestParam Long id, @RequestParam Boolean submittedAnswer,
                              HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        boolean isCorrect = quizService.checkAnswerAndSaveScore(id, submittedAnswer, loginMember.getId());

        if (isCorrect) {
            loginMember.increaseAnswerTrue();
        } else {
            loginMember.increaseAnswerFalse();
        }

        model.addAttribute("isCorrect", isCorrect);
        return "quiz/result";
    }
}