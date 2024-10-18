package br.com.livoltek.application.filter;

import br.com.livoltek.core.api.common.usecase.expression.GerarExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpressionFilter implements Filter {

    private static final String ENTITY_PACKAGE = "br.com.livoltek.core.internal.common.entity";
    private final GerarExpression gerarExpression;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String filtro = req.getParameter("filtro");
        if (Objects.nonNull(filtro) && !filtro.isBlank()) {
            List<String> filtrosList = Arrays.stream(filtro.split(";")).toList();
            Class<?> tipoEntidade = obterTipoEntidade(req);
            BooleanExpression expression = gerarExpression.executar(filtrosList, tipoEntidade);
            log.info("Expression filter: {}", expression);
            req.setAttribute("expression", expression);
        }
        chain.doFilter(request, response);
    }

    private Class<?> obterTipoEntidade(HttpServletRequest req) {
        try {
            String simpleClassName = req.getRequestURI().split("/")[1];
            String fqn = String.format("%s.%s", ENTITY_PACKAGE, obterNome(simpleClassName));
            log.info("Filtro FQN: {}", fqn);
            return Class.forName(fqn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Não foi possível identificar o tipo de entidade.");
        }
    }

    private static String obterNome(String simpleClassName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String part: simpleClassName.split("-")) {
            char[] c = part.toCharArray();
            c[0] = Character.toUpperCase(c[0]);
            stringBuilder.append(new String(c));
        }
        return stringBuilder.toString();
    }
}