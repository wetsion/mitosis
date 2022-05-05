package site.wetsion.framework.mitosis.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wetsion
 * @date 2022-05-04 15:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageR<T> implements Serializable {

    private Long page;

    private Long pageSize;

    private Long total;

    private List<T> data;
}
