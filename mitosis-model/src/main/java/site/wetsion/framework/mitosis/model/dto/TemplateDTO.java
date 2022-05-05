package site.wetsion.framework.mitosis.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:shuanghua@fordeal.com">霜华</a>
 * @date 2022-05-04 14:38
 */
@Data
public class TemplateDTO implements Serializable {
    private static final long serialVersionUID = -7230299641988079568L;

    private Long id;

    private String title;

    private String content;

    private Integer created;

    private Integer updated;
}
