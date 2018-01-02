package com.dianwoba.middleware.elastic.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import java.util.List;
import java.util.stream.Collectors;
import sun.net.util.IPAddressUtil;

public class EsAddressParser {


  public static ImmutableMap<String, Integer> parseToHostAndPort(String addresses) {
    List<String> addressList = Splitter.on(",").omitEmptyStrings().splitToList(addresses);
    Builder<String, Integer> ipPortMaps = ImmutableMap.builder();

    List<IpPort> ipPorts = addressList.stream().map(x -> {
      Iterable<String> ipAndPort = Splitter.on(":").omitEmptyStrings().split(x);
      String ip = Iterables.getFirst(ipAndPort, null);
      Preconditions.checkArgument(!Strings.isNullOrEmpty(ip), "ip is null or empty");

      // ipv4
      boolean iPv4LiteralAddress = IPAddressUtil.isIPv4LiteralAddress(ip);
      // ipv6
      boolean iPv6LiteralAddress = IPAddressUtil.isIPv6LiteralAddress(ip);
      Preconditions.checkState( iPv4LiteralAddress || iPv6LiteralAddress, "not a invalid ip  address, ip=%s", ip);

      String port = Iterables.getLast(ipAndPort);
      Integer portInt = Ints.tryParse(port);
      Preconditions.checkNotNull(portInt, "invalid port, port=%s", port);

      return new IpPort(ip, portInt);
    }).collect(Collectors.toList());

    Preconditions
        .checkState(ipPorts.size() == addressList.size(), "address can not parse, addres=%s",
            addresses);

    ipPorts.parallelStream().forEach(x ->
        ipPortMaps.put(x.getIp(), x.getPort()));
    return ipPortMaps.build();
  }

  private static class IpPort {

    private final String ip;
    private final int port;

    public IpPort(String ip, int port) {
      this.ip = ip;
      this.port = port;
    }

    public String getIp() {
      return ip;
    }

    public int getPort() {
      return port;
    }
  }


}
