package com.bitshammer.reports;

import com.bitshammer.model.MembroReportDTO;
import com.bitshammer.resports.jasper.JasperService;
import com.bitshammer.rest.ChurchMembersAPI;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Singleton
public class ReportsController {

    @Inject
    private ChurchMembersAPI churchMembersAPI;

    @Inject
    private JasperService jasperService;

    public HttpServletResponse juridico(Request req, Response res) throws Exception {
        List<MembroReportDTO> membros = churchMembersAPI.getMembers();
        byte[] report = jasperService.generateReport("reports/juridica.jrxml", membros);
        ServletOutputStream outputStream = res.raw().getOutputStream();
        res.raw().setContentType("application/octet-stream");
        res.raw().setHeader("Content-Disposition","attachment; filename=relatorioMembros.pdf");
        res.raw().getOutputStream().write(report);
        outputStream.flush();
        outputStream.close();
        return res.raw();
    }

}
