package jfw.util.ecs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ComponentMapTest {

	private static final int ID0 = 0;
	private static final int ID4 = 4;
	private static final int ID7 = 7;

	private static final int COMPONENT0 = 99;
	private static final int COMPONENT4 = 63;
	private static final int COMPONENT7 = 6;

	private ComponentMap<Integer> componentMap;

	@BeforeEach
	void setUp() {
		componentMap = new ComponentMap<>(Map.of(ID0, COMPONENT0, ID4, COMPONENT4, ID7, COMPONENT7));
	}

	@Test
	void testGetUnknownComponent() {
		assertThat(componentMap.get(2)).isEqualTo(Optional.empty());
	}

	@Test
	void testGetComponent() {
		assertThat(componentMap.get(ID0)).isEqualTo(Optional.of(COMPONENT0));
		assertThat(componentMap.get(ID4)).isEqualTo(Optional.of(COMPONENT4));
		assertThat(componentMap.get(ID7)).isEqualTo(Optional.of(COMPONENT7));
	}

	@Test
	void testGetAll() {
		assertThat(componentMap.getAll()).containsExactlyInAnyOrder(COMPONENT0, COMPONENT4, COMPONENT7);
	}

	@Test
	void testGetIds() {
		assertThat(componentMap.getIds()).containsExactlyInAnyOrder(ID0, ID4, ID7);
	}

	@Test
	void testVisit() {
		Visitor<Integer> visitor = mock(Visitor.class);

		componentMap.visit(visitor);

		verify(visitor).visit(ID0, COMPONENT0);
		verify(visitor).visit(ID4, COMPONENT4);
		verify(visitor).visit(ID7, COMPONENT7);
		verifyNoMoreInteractions(visitor);
	}

}