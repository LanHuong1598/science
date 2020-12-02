package vn.com.mta.science.module.schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class Stats {

    List<StatsByYear> ds;

    StatsByYear tong;

    StatsTotal tongso;

}
