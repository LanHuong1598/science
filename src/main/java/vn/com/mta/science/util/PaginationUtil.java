package vn.com.mta.science.util;

import vn.com.itechcorp.base.repository.dao.PaginationInfo;

import java.util.List;

public class PaginationUtil {

    public static List getPages(List list, PaginationInfo pageInfo) {
        if (list == null) return null;

        if (pageInfo == null || pageInfo.getFirstRow() == -1 || pageInfo.getMaxResults() == -1) return list;
        if (pageInfo.getFirstRow() > list.size()) return null;

        int lastRow = pageInfo.getFirstRow() + pageInfo.getMaxResults();
        return list.subList(pageInfo.getFirstRow(), Math.min(lastRow, list.size()));
    }
}
