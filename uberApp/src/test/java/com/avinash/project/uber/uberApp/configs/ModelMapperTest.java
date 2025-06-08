package com.avinash.project.uber.uberApp.configs;

import com.avinash.project.uber.uberApp.dto.PointDto;
import com.avinash.project.uber.uberApp.utils.GeometryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new MapperConfig().modelMapper(); // Reuse your MapperConfig
    }

    @Test
    public void testPointDtoToPointMapping() {
        PointDto pointDto = new PointDto(new double[]{45.767812, 13.551134});

        Point point = modelMapper.map(pointDto, Point.class);

        assertNotNull(point, "Point should not be null");
        assertEquals(45.767812, point.getX(), 0.000001);
        assertEquals(13.551134, point.getY(), 0.000001);
        assertEquals(4326, point.getSRID(), "SRID should be 4326");
    }

    @Test
    public void testPointToPointDtoMapping() {
        Point point = GeometryUtil.createPoint(new PointDto(new double[]{45.767812, 13.551134}));

        PointDto pointDto = modelMapper.map(point, PointDto.class);

        assertNotNull(pointDto, "PointDto should not be null");
        assertEquals(45.767812, pointDto.getCoordinates()[0], 0.000001);
        assertEquals(13.551134, pointDto.getCoordinates()[1], 0.000001);
    }
}

