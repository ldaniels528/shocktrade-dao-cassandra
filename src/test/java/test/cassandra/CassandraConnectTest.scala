package test.cassandra

import com.shocktrade.dao.cassandra._
import com.shocktrade.services.util.ResourceUtilities._
import com.shocktrade.services.util.Tabular

/**
 * <dependency>
 * 		<groupId>com.netflix.astyanax</groupId>
 * 		<artifactId>astyanax-cassandra</artifactId>
 * 		<version>1.56.44</version>
 * </dependency>
 *
 * <dependency>
 * 		<groupId>org.apache.cassandra</groupId>
 * 		<artifactId>cassandra-all</artifactId>
 * 		<version>2.0.3</version>
 * </dependency>
 *
 * <dependency>
 * 		<groupId>org.apache-extras.cassandra-jdbc</groupId>
 *   	<artifactId>cassandra-jdbc</artifactId>
 *   	<version>1.2.5</version>
 * </dependency>
 */
class CassandraConnectTest() extends ShockTradeCassandraDAO {
  import java.util.concurrent.TimeUnit
  import scala.collection.JavaConversions._
  import com.datastax.driver.core._
  import org.slf4j.LoggerFactory
  import org.junit.Test

  val logger = LoggerFactory.getLogger(getClass())
  val tabular = new Tabular()

  @Test
  def test() {
    implicit val cluster = getConnection
    try {
      val metadata = cluster.getMetadata()
      logger.info(s"Connected to cluster: ${metadata.getClusterName()}")
      for (host <- metadata.getAllHosts()) {
        logger.info("Datatacenter: %s Host: %s Rack: %s\n".format(
          host.getDatacenter(), host.getAddress(), host.getRack()))
      }

    } finally {
      cluster.close()
    }
  }

}
