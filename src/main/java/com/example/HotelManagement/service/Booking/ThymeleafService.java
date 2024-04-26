package com.example.HotelManagement.service.Booking;

import com.example.HotelManagement.model.Booking;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class ThymeleafService {
    private static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessages";
    private static final String MAIL_TEMPLATE_PREFIX = "/templates/";
    private static final String MAIL_TEMPLATE_SUFFIX = ".html";
    private static final String UTF_8 = "UTF-8";
    private static final String GUEST_TEMPLATE_NAME = "mail-template";
    private static final String HOST_TEMPLATE_NAME = "mail-template-hotel";
    private static final TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    public String getContentForGuest(Booking booking) {
        final Context context = new Context();

        context.setVariable("booking", booking);
        context.setVariable("checkInDate", booking.getCheckInDate());
        context.setVariable("checkOutDate", booking.getCheckOutDate());
        context.setVariable("totalPrice", formatCurrency(booking.getTotalPrice()));
        context.setVariable("guest", booking.getGuest());
        context.setVariable("hotelName", booking.getRoom().getHotel().getName());
        context.setVariable("hotelAddress", booking.getRoom().getHotel().getAddress());
        context.setVariable("roomType", booking.getRoom().getType().getName());
        context.setVariable("roomDescription", booking.getRoom().getType().getDescription());

        return templateEngine.process(GUEST_TEMPLATE_NAME, context);
    }

    public String getContentForHost(Booking booking) {
        final Context context = new Context();

        context.setVariable("booking", booking);
        context.setVariable("checkInDate", booking.getCheckInDate());
        context.setVariable("checkOutDate", booking.getCheckInDate());
        context.setVariable("totalPrice", formatCurrency(booking.getTotalPrice()));
        context.setVariable("guest", booking.getGuest());
        context.setVariable("hotelName", booking.getRoom().getHotel().getName());
        context.setVariable("hotelAddress", booking.getRoom().getHotel().getAddress());
        context.setVariable("roomType", booking.getRoom().getType().getName());
        context.setVariable("roomDescription", booking.getRoom().getType().getDescription());

        return templateEngine.process(HOST_TEMPLATE_NAME, context);
    }

    private String formatCurrency(String amount) {
        int intValue = Integer.parseInt(amount);
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        formatter.setGroupingUsed(true);
        formatter.setCurrency(java.util.Currency.getInstance("VND"));
        return formatter.format(intValue);
    }
}
