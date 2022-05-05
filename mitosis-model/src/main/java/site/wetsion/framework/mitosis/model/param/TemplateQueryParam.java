package site.wetsion.framework.mitosis.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:shuanghua@fordeal.com">霜华</a>
 * @date 2022-05-04 14:44
 */
@Data
public class TemplateQueryParam implements Serializable {
    private static final long serialVersionUID = 7606125179006059884L;

    private String title;
}
