package pl.lucasjasek.view;


import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class PdfViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        PDFBuilder view = new PDFBuilder();
        return view;
    }
}
